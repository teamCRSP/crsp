package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.PaymentPageDTO;
import com.csrp.csrp.dto.request.PaymentRequestDTO;
import com.csrp.csrp.dto.response.PageResponseDTO;
import com.csrp.csrp.dto.response.PaymentHistoryResponseDTO;
import com.csrp.csrp.dto.response.PaymentHistoryListResponseDTO;
import com.csrp.csrp.entity.*;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.*;
import com.csrp.csrp.token.TokenUserInfo;
import com.csrp.csrp.type.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ReservationHistoryRepository reservationHistoryRepository;
    private final ReservationDetailRepository reservationDetailRepository;
    private final ConcertInfoRepository concertInfoRepository;
    private final ConcertLocInfoRepository concertLocInfoRepository;
    private final TicketRepository ticketRepository;
    private final EachConcertInfoRepository eachConcertInfoRepository;
    private final TicketService ticketService;

    // 결제 등록
    // 결제 성공 -> 예매 내역 등록 -> 예매 내역 상세 등록 -> 티켓 발부 -> 결제 내역 등록
    public void paymentDone(List<PaymentRequestDTO> request, TokenUserInfo tokenUserInfo) {
        ReservationHistory reservationHistory = null;
        List <Ticket> tickets = null;
        try {
            int amount = 0;
            for (PaymentRequestDTO paymentRequestDTO : request) {
                amount += (paymentRequestDTO.getTicketCount() * paymentRequestDTO.getConcertSeatPrice());
            }
            ConcertInfo concertInfo = concertInfoRepository.findById(request.get(0).getConcertInfoId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
            User user = userRepository.findById(tokenUserInfo.getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
            // 예매 내역 등록
            reservationHistory = ReservationHistory.builder()
                    .amount(amount)
                    .user(user)
                    .concertInfo(concertInfo)
                    .build();
            ReservationHistory saveReservation = reservationHistoryRepository.save(reservationHistory);
            // 예매 내역 상세 등록
            for (PaymentRequestDTO paymentRequestDTO : request) {
                ReservationDetail reservationDetail = ReservationDetail.builder()
                        .endDate(paymentRequestDTO.getEndDate())
                        .startDate(paymentRequestDTO.getStartDate())
                        .concertSeat(paymentRequestDTO.getConcertSeat())
                        .concertSeatPrice(paymentRequestDTO.getConcertSeatPrice())
                        .ticketCount(paymentRequestDTO.getTicketCount())
                        .concertLocInfo(concertLocInfoRepository.findById(paymentRequestDTO.getConcertInfoId())
                                .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_LOCATION_NOT_FOUND)))
                        .reservationHistory(reservationHistoryRepository.findById(saveReservation.getId())
                                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY)))
                        .build();
                ReservationDetail save = reservationDetailRepository.save(reservationDetail);

                ConcertLocInfo concertLocInfo = concertLocInfoRepository.findById(paymentRequestDTO.getConcertInfoId())
                        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_LOCATION_NOT_FOUND));
                EachConcertInfo eachConcertInfo = eachConcertInfoRepository.findByConcertLocInfo(concertLocInfo);

                if (save.getConcertSeat().equals("seatA") && eachConcertInfo.getSeatA() - save.getTicketCount() >= 0) {
                    eachConcertInfo.setSeatA(eachConcertInfo.getSeatA() - save.getTicketCount());
                    eachConcertInfoRepository.save(eachConcertInfo);
                } else if (save.getConcertSeat().equals("seatB") && eachConcertInfo.getSeatB() - save.getTicketCount() >= 0) {
                    eachConcertInfo.setSeatB(eachConcertInfo.getSeatB() - save.getTicketCount());
                    eachConcertInfoRepository.save(eachConcertInfo);
                } else if (save.getConcertSeat().equals("seatS") && eachConcertInfo.getSeatS() - save.getTicketCount() >= 0) {
                    eachConcertInfo.setSeatS(eachConcertInfo.getSeatS() - save.getTicketCount());
                    eachConcertInfoRepository.save(eachConcertInfo);
                } else {
                    // 남아있는 티켓 수가 부족합니다.
                    throw new CustomException(ErrorCode.THE_NUMBER_OF_TICKETS_IS_INSUFFICIENT);
                }

                // 티켓 발부
                tickets = ticketService.getTicket(save, user);
            }


            // 결제 내역 등록
            paymentRepository.save(PaymentHistory.toEntity(user, reservationHistory));
        } catch (Exception e) { // 결제 실패 시 예매 내역 및 티켓 삭제
            if (reservationHistory != null) {
                reservationHistoryRepository.delete(reservationHistory);
            }
            if (tickets != null) {
                ticketRepository.deleteAll(tickets);
            }
            throw new RuntimeException(e);
        }

    }

    // 결제 내역 조회
    public PaymentHistoryListResponseDTO paymentHistoryList(PaymentPageDTO paymentPageDTO, TokenUserInfo tokenUserInfo) {
        PageRequest pageRequest = PageRequest.of(
                paymentPageDTO.getPage() - 1,
                paymentPageDTO.getSize(),
                Sort.by("id").descending()
        );

        User user = userRepository.findById(tokenUserInfo.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
        List<PaymentHistoryResponseDTO> toDto = new ArrayList<>();
        Page<PaymentHistory> byUser = paymentRepository.findByUser(user, pageRequest);
        for (PaymentHistory paymentHistory : byUser) {
            ReservationHistory reservationHistory = reservationHistoryRepository.findById(paymentHistory.getReservationHistory().getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY));
            ConcertInfo concertInfo = concertInfoRepository.findById(reservationHistory.getConcertInfo().getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
            PaymentHistoryResponseDTO paymentHistoryResponseDTO = new PaymentHistoryResponseDTO(concertInfo, paymentHistory);
            toDto.add(paymentHistoryResponseDTO);
        }

        return PaymentHistoryListResponseDTO.builder()
                .count(toDto.size())
                .pageResponseDTO(new PageResponseDTO(byUser))
                .paymentHistories(toDto)
                .build();
    }
}

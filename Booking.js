import React, { useState } from 'react';
import axios from 'axios';

const Booking = () => {
    const [selectedSeat, setSelectedSeat] = useState(null);

    const handleSeatSelection = (seat) => {
        setSelectedSeat(seat);
    };

    const handlePayment = () => {
        if (selectedSeat) {
            axios.post('/api/payment', { seat: selectedSeat })
                .then(response => {
                    // 결제 성공 처리
                    alert('결제가 성공적으로 완료되었습니다.');
                })
                .catch(error => {
                    // 결제 실패 처리
                    alert('결제에 실패했습니다.');
                });
        } else {
            alert('좌석을 선택해주세요.');
        }
    };

    return (
        <div>
            <h2>좌석 선택 및 결제</h2>
            <div className="seat-selection">
                {[1, 2, 3, 4, 5].map(seat => (
                    <button
                        key={seat}
                        onClick={() => handleSeatSelection(seat)}
                        className={selectedSeat === seat ? 'selected' : ''}
                    >
                        {seat}번 좌석
                    </button>
                ))}
            </div>
            <button onClick={handlePayment}>결제하기</button>
        </div>
    );
};

export default Booking;
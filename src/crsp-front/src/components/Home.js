import "../styles/Home.css";
import Slider from "react-slick";
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import '../styles/Slider.css';
import concertVideo from "../assets/concert.mp4";

function Home(){
  var settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1
  };

  
    return (
    <div>
<header className="header">
  <a href="" className="logo">CRSP</a>
  <input className="menu-btn" type="checkbox" id="menu-btn" />
  <label className="menu-icon" htmlFor="menu-btn"><span className="navicon"></span></label>
  <ul className="menu">
  <li><a href="/register">회원가입</a></li>
        <li><a href="/login">로그인</a></li>
        <li><a href="/#">로그아웃</a></li>
        <li><a href="/myInfo">내정보</a></li>
  </ul>
</header>

   <div className="showcase">
   <video className="bg-video-content" autoPlay muted loop>
      <source src={concertVideo} type="video/mp4"></source>
   </video>
   </div>


   <div className="slider-containers">

    <div className="slider-container">
    <Slider {...settings}>
    
      <div className="card">
        <img src="https://i.ibb.co/LZPVKq9/card1.png" alt="" />
        <h3>최신 콘서트 1</h3>
        <p>
          가수 이름
        </p>
        <p>
          장소
        </p>
        <p>
          일시
        </p>
        <a href="/MyInfo">예매하기<i className="fas fa-chevron-right"></i></a>
      </div>


      <div className="card">
        <img src="https://i.ibb.co/KjGFHVJ/card2.png" alt="" />
        <h3>최신 콘서트 2</h3>
        <p>
          가수 이름
        </p>
        <p>
          장소
        </p>
        <p>
          일시
        </p>
        <a href="/MyInfo">예매하기<i className="fas fa-chevron-right"></i></a>
      </div>

      <div className="card">
        <img src="https://i.ibb.co/2cnshH6/card3.png" alt="" />
        <h3>최신 콘서트 3</h3>
        <p>
          가수 이름
        </p>
        <p>
          장소
        </p>
        <p>
          일시
        </p>
        <a href="/MyInfo">예매하기<i className="fas fa-chevron-right"></i></a>
      </div>

      <div className="card">
        <img src="https://i.ibb.co/G57P0Pb/card4.png" alt="" />
        <h3>최신 콘서트 4</h3>
        <p>
          가수 이름
        </p>
        <p>
          장소
        </p>
        <p>
          일시
        </p>
        <a href="/MyInfo">예매하기<i className="fas fa-chevron-right"></i></a>
      </div>

    </Slider>
 </div>




    <div className="slider-container">
    <Slider {...settings}>
    <div className="card">
					<img src="https://i.ibb.co/zVqhWn2/card5.png" alt="" />
					<h3>콘서트 1</h3>
                    <p>
                     가수 이름
                    </p>
                    <p>
                      장소
                    </p>
                    <p>
                    일시
                    </p>
					<a href="#">예매하기 <i className="fas fa-chevron-right"></i></a>
				</div>
        <div className="card">
					<img src="https://i.ibb.co/mGZcxcn/card6.jpg" alt="" />
					<h3>콘서트 2</h3>
                    <p>
                     가수 이름
                    </p>
                    <p>
                      장소
                    </p>
                    <p>
                    일시
                    </p>
					<a href="#">예매하기 <i className="fas fa-chevron-right"></i></a>
				</div>
        <div className="card">
					<img src="https://i.ibb.co/NpPvVHj/card7.png" alt="" />
                    <h3>콘서트 3</h3>
                    <p>
                     가수 이름
                    </p>
                    <p>
                      장소
                    </p>
                    <p>
                    일시
                    </p>
					<a href="#">예매하기 <i className="fas fa-chevron-right"></i></a>
				</div>
        <div className="card">
					<img src="https://i.ibb.co/LkP4L5T/card8.png" alt="" />
					<h3>콘서트 4</h3>
                    <p>
                     가수 이름
                    </p>
                    <p>
                      장소
                    </p>
                    <p>
                    일시
                    </p>
					<a href="#">예매하기 <i className="fas fa-chevron-right"></i></a>
				</div>
 
        </Slider>

      </div>

      </div> 
      <section className="carbon dark">
        <div className="content">
          <h2>광고</h2>
          <p>광고 소개</p>
            <a href="#" className="btn">
              더 알아보기 <i className="fas fa-chevron-right"></i>
            </a>
        </div>
      </section>

      <section className="follow">
        <p>Follow CRSP</p>
        <a href="https://facebook.com">
          <img src="https://i.ibb.co/LrVMXNR/social-fb.png" alt="Facebook" />
        </a>
        <a href="https://twitter.com">
          <img src="https://i.ibb.co/vJvbLwm/social-twitter.png" alt="Twitter" />
        </a>
        <a href="https://linkedin.com">
          <img src="https://i.ibb.co/b30HMhR/social-linkedin.png" alt="Linkedin" />
        </a>
      </section>


      <section className="links">
        <div className="links-inner">
          <ul>
            <li><h3>(주) CRSP</h3></li>
            <li>주소 서울 서초구 강남대로 447 남서울빌딩 6층</li>
            <li>사업자등록번호 824-81-02515</li>
            <li>통신판매업신고 2022-서울강남-02179</li>
            <li>관광사업증 등록번호 : 제2014-42호</li>
          </ul>
          <ul>
            <li><h3>전자금융거래 분쟁처리 담당</h3></li>
            <li>투어 1500-0000｜티켓 1500-0000</li>
            <li>팩스 02-000-0000｜이메일</li>
            <li>intersolution@crsp.com</li>
            <li>개인정보보호책임자 cpo@crsp.com</li>
          </ul>
          <ul>
            <li><h3>고객센터</h3></li>
            <li>투어 1500-0000｜티켓 1500-0000</li>
            <li>팩스 02-000-0000｜이메일</li>
            <li>helpdesk@crop.com</li>
          </ul>
        </div>
      </section>



    <div id="footer">
      <footer className="footer">
        <div className="footer-inner">
          <div><i className="fas fa-globe fa-2x"></i> Korean (Seoul)</div>
          <ul>
            <li><a href="#">Sitemap</a></li>
					<li><a href="#">Contact CRSP</a></li>
					<li><a href="#">Privacy & cookies</a></li>
					<li><a href="#">Terms of use</a></li>
					<li><a href="#">Trademarks</a></li>
					<li><a href="#">Safety & eco</a></li>
					<li><a href="#">About our ads</a></li>
					<li><a href="#">&copy; CRSP</a></li>
          </ul>
        </div>
        </footer>
    </div>



    </div>
    );
}

export default Home;
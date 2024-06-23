import logo from './logo.svg';
import './App.css';
import { useEffect, useState } from 'react';

function App() {
  const [data, setData] = useState(null);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await fetch('http://localhost:8080/');
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const textData = await response.text(); // 서버가 JSON이 아닌 문자열을 반환하기 때문에 .text() 사용
      setData(textData);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          {data}
        </a>
        {data && (
          <div>
            <h2>Data from API:</h2>
            <pre>{data}</pre>
          </div>
        )}
      </header>
    </div>
  );
}

export default App;

import logo from './logo.svg';
import './App.css';
import Greeting from './Greeting';
import Counter from './Counter';

function App() {
  const userName = "Ayusman Pradhan"; // Dynamic value that can be changed

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <Greeting name={userName} />
        <Counter />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;

import './App.css';
import StartButton from './Components/StartButton';
import NewGame from './Components/Forms/NewGame';

function App() {
  const startText = "Start"
  const iterator = {value: 0};
  return (
    <div className='App'>
      Welcome to Fortune Hangman!
      <StartButton text={startText} iterator={iterator}></StartButton>
      <NewGame></NewGame>
    </div>
  );
}

export default App;

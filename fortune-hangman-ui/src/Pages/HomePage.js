import '../App.css';
// import { useState } from 'react';
import Button from '../Components/Button';


function HomePage() {
  const startText = "Start"

  // const [game, setGame] = useState({
  //   gameId:null,
  //   gameName:null,
  //   roundId:null,
  //   wordId:null,
  //   gameStatus:null,
  //   letterGuessed:null,
  //   totalTeam:null
  // });

  // const addNewGameHandler = (event) =>{

  //   setGame((prevState)=> {
  //     return {...prevState,gameNam: event.target.value}
  //   });
  // };

  return (
    <div className='App'>
      Welcome to Fortune Hangman!
      <Button link="/getStarted" text={startText}></Button>
    </div>
  );
}
export default HomePage;
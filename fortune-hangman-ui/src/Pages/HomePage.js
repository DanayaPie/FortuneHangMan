import '../App.css';
// import { useState } from 'react';
import Button from '../Components/Button';


function HomePage() {
  const startText = "Start"

  return (
    <div className='App'>
      Welcome to Fortune Hangman!
      <Button link="/getStarted" text={startText}></Button>
    </div>
  );
}
export default HomePage;
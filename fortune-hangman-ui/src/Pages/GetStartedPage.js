import '../App.css';
// import { useState } from 'react';
import Button from '../Components/Button';


function GetStartedPage() {

  return (
    <div className='App'>
      Continue Game or Start New Game?
      <Button text='Continue'></Button>
      <Button link='/newGame' text='Start New Game'></Button>
    </div>
  );
}
export default GetStartedPage;
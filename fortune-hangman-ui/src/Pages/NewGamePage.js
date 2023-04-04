import '../App.css';
// import { useState } from 'react';
import NewGame from '../Components/Forms/NewGame';
import TeamCount from '../Components/Forms/TeamCount';


function NewGamePage() {

  return (
    <div className='App'>
      <NewGame/>
      <TeamCount/>
    </div>
  );
}
export default NewGamePage;
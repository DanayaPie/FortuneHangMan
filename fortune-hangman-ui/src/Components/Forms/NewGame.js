import React, { useState } from 'react';

function NewGame(props) {

  const [gameName, setGameName] = useState('');

  function gameNameChangeHandler(event) {
    setGameName(event.target.value);
  }

  function submitHandler(e) {
    e.preventDefault();
    console.log(`Game ${gameName} created!`)
    setGameName('');
  }

  return (
    <form className='' onSubmit={(e) => submitHandler(e)}>
      <label htmlFor="gname">Enter Game name</label>
      <br />
      <input type="text" id="gname" value={gameName} name="gname" onChange={(e) => gameNameChangeHandler(e)}></input>
      <br />
      <button type="submit">Create Game</button>
    </form>
  );
}



export default NewGame;
import React, { useState } from 'react';

function NewGame(props) {

  const [gameName, setGameName] = useState('');

  function gameNameChangeHandler(event) {
    console.log(event.target.value);
    setGameName(event.target.value);
  }

  function submitHandler(e, input) {
    e.preventDefault();
    console.log(`Game ${input} created!`)
  }

  return (
    <form className='' onSubmit={(e) => submitHandler(e, gameName)}>
      <label for="gname">Enter Game name</label>
      <br />
      <input type="text" id="gname" name="gname" onChange={(e) => gameNameChangeHandler(e)}></input>
      <button type="submit">Create Game</button>
    </form>
  );
}



export default NewGame;
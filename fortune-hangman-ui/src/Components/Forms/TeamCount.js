import React, { useState } from 'react';

function TeamCount(props) {

  const [tCount, setTcount] = useState('');

  function teamCountChangeHandler(event) {
    console.log(event.target.value);
    setTcount(event.target.value);
  }

  function submitHandler(e) {
    e.preventDefault();
    console.log(`Team Count ${tCount} created!`)
    setTcount('');
  }

  return (
    <form className='' onSubmit={(e) => submitHandler(e)}>
      <label htmlFor="tcount">How many teams are playing? 2-4</label>
      <br />
      <input type="Number" min="2" max="4" id="tcount" value={tCount} name="tcount" onChange={(e) => teamCountChangeHandler(e)}></input>
      <br />
      <button type="submit">Submit</button>
    </form>
  );
}



export default TeamCount;
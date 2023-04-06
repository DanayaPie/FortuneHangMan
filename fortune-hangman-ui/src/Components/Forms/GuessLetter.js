function GuessLetter(props) {

  function submitHandler(e) {
    e.preventDefault();
    props.onLetterSubmit();
  }

  return (
    <form className='' onSubmit={(e) => submitHandler(e)}>
      <label htmlFor="guess">Guess a letter | Buy a vowel for 250</label>
      <br />
      <input type="text" id="guess" pattern="[a-zA-Z]+" value={props.letterGuessed} maxLength={1} name="guess" onChange={(e) => props.onLetterGuessedChange(e)}></input>
      <br />
      <button className= "btn btn-primary" type="submit">submit</button>
    </form>
  );
}

export default GuessLetter;
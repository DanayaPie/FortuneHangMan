function SolveThePuzzle(props) {

  function submitHandler(e) {
    e.preventDefault();
    props.onPuzzleGuessSubmit();
  }

  return (
    <form className='' onSubmit={(e) => submitHandler(e)}>
      <label htmlFor="guess">Solve the Puzzle</label>
      <br />
      <input type="text" id="guessPuzzle" pattern="[a-zA-Z]+" value={props.puzzleGuess}  name="guessPuzzle" onChange={(e) => props.onPuzzleGuessChange(e)}></input>
      <br />
      <button className= "btn btn-primary" type="submit">submit</button>
    </form>
  );
}

export default SolveThePuzzle;
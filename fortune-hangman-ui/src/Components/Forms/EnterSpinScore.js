function EnterSpinScore(props) {

  function submitHandler(e) {
    e.preventDefault();
    props.onSpinScoreSubmit();
    e.target[0].selectedIndex = 0
  }
  function badSpinHandler(e) {
    e.preventDefault();
    console.log("You suck");
    props.onBadSpin(e.target.value);
  }

  return (
    <form className='' onChange={(e) => props.onSelectSpinScoreChange(e)} onSubmit={(e) => submitHandler(e)}>
      {/* <label htmlFor="spinScore">Select Spin Amount</label> */}
      <br />
      <select id="spinScore" name="spinScore" defaultValue={"default"}>
        <option value="default" disabled hidden>Select Spin Score</option>
        <option value="500" >500</option>
        <option value="550">550</option>
        <option value="600">600</option>
        <option value="650">650</option>
        <option value="700">700</option>
        <option value="900">900</option>
        <option value="1000">1000</option>
        <option value="3500">3500</option>
      </select>
      <br />
      <button className="btn btn-warning" onClick={badSpinHandler} value="Lose">Lose A turn</button>
      <button className="btn btn-primary" type="submit" value="Submit">submit</button>
      <button className="btn btn-danger" onClick={badSpinHandler} value="Bankrupt">Bankrupt</button>
    </form>
  );
}

export default EnterSpinScore;
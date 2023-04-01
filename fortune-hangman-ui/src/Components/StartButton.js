import './StartButton.css'
function StartButton(props) {
    return (
      <div className='btn btn-primary btn-lg' onClick={() => buttonClicked(props.iterator)}> 
        {props.text}
      </div>
    );
  }

  function buttonClicked(i){
    i.value++
    console.log("Hello!" + i.value)
  }
  
  export default StartButton;
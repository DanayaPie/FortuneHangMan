function Letter(props) {

    //Lift this state up to game, handle changes then do visibility check in game
    // const [letterVisibility, setLetterVisibility] = useState('');

return (
    <div className="letter">
        {props.showGuessedLetters(props.letter)? props.letter.toUpperCase() : ''}
    </ div>
)
}

export default Letter;
import Letter from "./Letter";


function wordToLetters(word) {
    return word.split("");
}

function Word(props) {
    const letters = wordToLetters(props.word)

    return (
        <div className='wordWrapper'>
            {/* Dynamic list of letters needed */}
            {letters.map((letter) => (
                <Letter letter={letter.toUpperCase()}
                    key={props.wordId + Math.random()}
                    lettersGuessed={props.lettersGuessed} 
                />
            ))}
        </div>
    )
}

export default Word;
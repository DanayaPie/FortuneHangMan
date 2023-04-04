
function TeamCard(props) {

    return (
        <div className='card team'>
            <p>{props.teamName}</p>
            <p>Score: {props.roundScore.roundScore}</p>
        </div>
    );
}


export default TeamCard;
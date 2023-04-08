function WinningTeam(props) {

    const winningTeam = props.teams.reduce((scoreAccum, team) => {
        return team.teamScore > scoreAccum.teamScore ? team : scoreAccum
    }, props.teams[0]);

    return (
        <div className='category'>
            Winning team is {winningTeam.teamName}!
            <p>With a score of {winningTeam.totalScore}</p>
            {props.category}
        </ div>
    )
}

export default WinningTeam;
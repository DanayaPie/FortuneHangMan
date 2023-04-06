import './Game.css'
import TeamCard from './TeamCard';




function TeamBox(props) {
    return (
        <div className='teamBox'>
            {props.teams.map((team) => (
                <TeamCard
                    key={team.teamId}
                    teamName={team.teamName}
                    roundScore={props.roundScores.find((round) => round.teamId === team.teamId)}
                    teamTurn={team.teamTurn}
                    currentTeamTurn={props.currentTeamTurn}
                />
            ))}
        </div>
    );
}

export default TeamBox;
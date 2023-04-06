import './Button.css'
import { Link } from 'react-router-dom';

function Button(props) {
    return (
      <div className='btn btn-primary btn-lg'> 
        <Link to={props.link}> {props.text} </Link>
      </div>
    );
  }

  
  export default Button;
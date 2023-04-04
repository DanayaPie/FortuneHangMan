import './App.css';
import { createBrowserRouter,RouterProvider} from 'react-router-dom';
import HomePage from './Pages/HomePage';
import GetStartedPage from './Pages/GetStartedPage';
import NewGamePage from './Pages/NewGamePage';
import GamePage from './Pages/GamePage';
import Root from './Pages/Root';

const router = createBrowserRouter([
  {
    path:'/',
    element:<Root/>,
    children:[  
      {path:'/',element:<HomePage/>},
    {path:'/getStarted',element:<GetStartedPage/>},
    {path:'/newGame',element:<NewGamePage/>},
    {path:'/game',element:<GamePage/>}
  ]
  },


]);

function App() {
  return ( <RouterProvider router={router}  />);
}

export default App;

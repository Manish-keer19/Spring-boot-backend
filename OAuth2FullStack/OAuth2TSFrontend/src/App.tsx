import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import LoginPage from './Component/LoginPage'
import Dashboard from './Component/Dashboard'
import Greet from './Component/Greet'


const route = createBrowserRouter([
  
  {
    path:'/',
    element:<LoginPage></LoginPage>
  },
  {
    path:"/dashboard",
    element:<Dashboard/>
  },
  {
    path:"/greet",
    element:<Greet/>
  }
])
function App() {
  return (
    <>
    <RouterProvider router={route}></RouterProvider>
    </>
  )
}

export default App
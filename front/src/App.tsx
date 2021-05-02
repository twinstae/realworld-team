import React from 'react';
import './App.css';
import {ArticleList} from './components/ArticleList/ArticleList';
import { QueryClient, QueryClientProvider } from 'react-query'
 
const queryClient = new QueryClient()

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <div className="App">
        <ArticleList />
      </div>
    </QueryClientProvider>
  );
}

export default App;

import {FunctionComponent, useEffect, useState} from 'react';
import {Article} from '../Article/Article';
import {ArticleT} from '../data';
import "./ArticleList.css";

export interface ArticleListProps {}


interface GetArticleData {
  articles: ArticleT[],
  articlesCount: number
}

const realGetArticleList = async ()=>{
  const url = "https://conduit.productionready.io/api/articles"
  const res = await fetch(url);
  const data: GetArticleData = await res.json();
  console.log(data);
    
  return data.articles;
};


export const ArticleList: FunctionComponent<ArticleListProps> = () => {
  const [articles, setArticles] = useState<ArticleT[]>([]);

  useEffect(()=> {
    realGetArticleList()
      .then(articlesData => setArticles(articlesData));
  }, []) // 빈 의존성 배열 중요!!!

  return (
    <ul className="ArticleList">
      {articles.map(article => <li><Article article={article} /></li>)}
    </ul>
  );
}


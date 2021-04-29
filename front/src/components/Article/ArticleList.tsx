import {FunctionComponent, useEffect, useState} from 'react';
import {Article, ArticleT} from './Article';

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

const fakeGetArticleList = async () => {
  const data: GetArticleData = {
    "articles":[
      {"title":"Test","slug":"test-ypvr4i","body":"Test 2","createdAt":"2021-04-29T01:15:11.616Z","updatedAt":"2021-04-29T01:15:11.616Z","tagList":[],"description":"Test 1","author":{"username":"Davitorino","bio":null,"image":"https://static.productionready.io/images/smiley-cyrus.jpg","following":false},"favorited":false,"favoritesCount":1},
      {"title":"Test","slug":"test-79ed98","body":"Test2","createdAt":"2021-04-29T01:06:02.727Z","updatedAt":"2021-04-29T01:06:02.727Z","tagList":[],"description":"Test1","author":{"username":"xyx1990","bio":null,"image":"https://static.productionready.io/images/smiley-cyrus.jpg","following":false},"favorited":false,"favoritesCount":2}
    ],
    "articlesCount": 500
  };

  return data.articles;  
}


export const ArticleList: FunctionComponent<ArticleListProps> = () => {
  const [articles, setArticles] = useState<ArticleT[]>([]);

  useEffect(()=> {
    fakeGetArticleList()
      .then(articlesData => setArticles(articlesData))
  })

  return (
    <ul className="ArticleList">
      {articles.map(article => <li><Article article={article} /></li>)}
    </ul>
  );
}


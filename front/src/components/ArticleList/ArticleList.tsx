import React, {FunctionComponent, useEffect, useState} from 'react';
import {Article} from '../Article/Article';
import {ArticleT} from '../data';
import "./ArticleList.css";
import { useQuery } from 'react-query';
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

interface Errors {
  errors: {
    body: string[]
  }
}

export const ArticleList: FunctionComponent<ArticleListProps> = () => {
  const { isLoading, isError, data, error } = useQuery<ArticleT[], Errors>('articles', realGetArticleList)

  if (isLoading){
    return <div>로딩 중</div>
  }
  if (isError && error) {
    return (
        <ul>
          {error.errors.body.map(error=><li>{error}</li>)}
        </ul>
      )
   }

  return (
    <ul className="ArticleList">
      {data && data.map(article => <li><Article article={article} /></li>)}
    </ul>
  );
}


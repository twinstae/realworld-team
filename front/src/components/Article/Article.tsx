import React, {FunctionComponent} from "react";
import {ArticleT} from "../data";
import "./Article.css";


export interface ArticleProps {
  article: ArticleT
};

export const Article: FunctionComponent<ArticleProps> = ({article}) =>{
  const localeCreatedAt = new Date(article.createdAt).toLocaleDateString();
  
  return (
    <div className="article" >
      <h3>
        {article.title}
        <span> {article.author.username}</span>
      </h3>
      <span>{localeCreatedAt}</span>
      <p>{article.description}</p>
    </div>
  )
};

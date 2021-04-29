import {FunctionComponent} from "react";
import "./Article.css";

export type ArticleT = {
  createdAt: string,
  updatedAt: string,
  slug: string,
  title: string,
  description: string,
  body: string,
  tagList: string[],
  author: {
    username: string,
    bio: string | null,
    image: string,
    following: boolean,
  }
  favorited: boolean,
  favoritesCount: number,
}

export interface ArticleProps {
  article: ArticleT
};

export const Article: FunctionComponent<ArticleProps> = ({article}) =>{
  const localeCreatedAd = new Date(article.createdAt).toLocaleDateString();
  
  return (
    <div className="article" >
      <h3>{article.title}</h3>
      <span> {localeCreatedAd}</span>
      <p>{article.description}</p>
    </div>
  )
};

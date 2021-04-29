import {FunctionComponent} from "react";
import "./Article.css";

export type ArticleProps = {
  article: {
    createdAt: string,
    updatedAt: string,
    id: number,
    slug: string,
    title: string,
    description: string,
    body: string,
    tagList: string[],
    //author: Profile
    favorited: boolean,
    favoritesCount: number,
  }
};

export const Article: FunctionComponent<ArticleProps> = ({article}) =>{
  const localeCreatedAd = new Date(article.createdAt).toLocaleDateString();
  
  return (
    <div className="article" >
      <span> {localeCreatedAd}</span>
      <h3 className="article-title">{article.title}</h3>
      <p>{article.description}</p>
    </div>
  )
};

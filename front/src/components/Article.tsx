import {FunctionComponent} from "react";

export type ArticleProp = {
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
}

export const Article: FunctionComponent<ArticleProp> = ({article}) =>
  (
    <div className="Article">
      <div className="ArticleHeader">{article.createdAt}</div>
      <div className="ArticlePreview">
        <h3>{article.title}</h3>
        <p>{article.description}</p>
      </div>
    </div>
);

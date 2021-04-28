import {FunctionComponent} from "react";

type ArticleProp = {
  article: {
    body: string
  }
}

export const Article: FunctionComponent<ArticleProp> = ({article}) => <div>{article.body}</div>;

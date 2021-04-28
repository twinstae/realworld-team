import {FunctionComponent} from "react";

type ArticleProp = {
  article: {
    body: string
  }
}

export const Article: FunctionComponent<ArticleProp> = ({article}) =>
  (
    <div>
      {/* 헤더 */}
      {/* ??? */}
      {article.body}
      {/* ??? */}
    </div>
  );

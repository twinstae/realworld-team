import {ArticleT} from "../components/Article/Article";

const API_ROOT = 'https://conduit.productionready.io/api';

const getBody = (res: Response) => res.json();

const requests = {
  get: (url: string) =>
    fetch(API_ROOT + url).then(getBody)
};

const limit = (count: number, p: number) => `limit=${count}&offset=${p ? p * count : 0}`;

interface ArticlesListResponse {
  articles: ArticleT[]
  articlesCount: number
}

const Articles = {
  all: async function(page: number): Promise<ArticlesListResponse>{
      return requests.get(`/articles?${limit(10, page)}`);
    }
};

export default {
  Articles
}

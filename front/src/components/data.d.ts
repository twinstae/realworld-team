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


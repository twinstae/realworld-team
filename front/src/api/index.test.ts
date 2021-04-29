import api from "./index";

describe('api.Articles', ()=>{
  it('api.Articles.all(1)은 article[]와 articleCount를 반환한다.', async ()=>{
    const data = await api.Articles.all(1);

    expect(data.articles.length).toBe(10);
    expect(data.articlesCount).not.toBeNull()
  })
})

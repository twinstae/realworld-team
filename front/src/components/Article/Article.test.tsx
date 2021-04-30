import { shallow } from 'enzyme';
import {Article} from './Article';

export const articleDummy = {
    "createdAt": "2020-01-07T16:20:28.150180Z",
    "updatedAt": "2020-01-07T16:20:28.150180Z",
    "id": 2,
    "slug": "deep-dive-into-websockets-e6c4c7622423",
    "title": "Deep Dive into WebSockets",
    "description": "Understand the important attributes of WebSockets that every developer should know",
    "body": "Inthe early days of the Internet, web applications were built around HTTP requests triggered by user interactions. With the advancement of technology, the requirement for real-time data transmission and two-way communication emerged. It was a requirement for low-latency applications such as,",
    "tagList": [
      "Websocket",
      "Real Time Communication",
      "JavaScript",
      "Low Latency",
      "Computer Programming"
    ],
    "author": {
      "username": "Aredruss",
      "bio": "",
      "image": "https://static.productionready.io/images/smiley-cyrus.jpg",
      "following": false
    },
    "favorited": false,
    "favoritesCount": 0
  };
 

describe('<Article />', () => {
  it('<Article /> 컴포넌트는 에러 없이 렌더링 된다.', () => {
    const wrapper = shallow(<Article article={articleDummy}/>);
    expect(wrapper).toBeVisible();
  });

  // Article에는 글쓴이 표시, 날짜 표시가 있어야 한다.

  // Article에는 제목과 미리보기Preview, 더 읽기Read more이 있어야 한다
  
  // Article에는 ~ 있어야 한다.
  
  // Article에는 ~ 있어야 한다.
});

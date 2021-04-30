import { shallow } from 'enzyme';
import {Article} from './Article';
import {articleDummy} from './Article.fixture';

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

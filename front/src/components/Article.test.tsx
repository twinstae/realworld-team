import { shallow } from 'enzyme';
import {Article} from './Article';

describe('<Article />', () => {
  it('<Article /> 컴포넌트는 에러 없이 렌더링 된다.', () => {
    const content = "Article"
    const wrapper = shallow(<Article article={{body: content}}/>);
    expect(wrapper.text()).toEqual(content);
  });

  // Article에는 Header가 있어야 한다.  

  // Article에는 제목과 미리보기Preview, 더 읽기Read more이 있어야 한다
  
  // Article에는 ~ 있어야 한다.
  
  // Article에는 ~ 있어야 한다.
});

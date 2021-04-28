import { shallow } from 'enzyme';
import {Article} from './Article';

describe('<Article />', () => {
  it('renders <Article /> components', () => {
    const content = "Article"
    const wrapper = shallow(<Article article={{body: content}}/>);
    expect(wrapper.text()).toEqual(content);
  });
});

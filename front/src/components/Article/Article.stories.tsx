import {Story} from '@storybook/react';
import {articleDummy} from './Article.test';
import {Article, ArticleProps} from './Article';

export default {
  title: "component/Article",
  component: Article,
}

const Template: Story<ArticleProps> = (args) => <Article {...args} />;

export const Primary = Template.bind({});
Primary.args = {
  article: articleDummy
}

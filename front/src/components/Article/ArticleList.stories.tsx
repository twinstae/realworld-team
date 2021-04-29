import { Story } from '@storybook/react';
import { ArticleList, ArticleListProps } from './ArticleList';

export default {
  title: "component/ArticleList",
  component: ArticleList
}

const Template: Story<ArticleListProps> = (args) => <ArticleList {...args} />;

export const Primary = Template.bind({});

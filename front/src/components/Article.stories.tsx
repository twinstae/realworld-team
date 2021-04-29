import React from 'react';
import {Story} from '@storybook/react';
import { Article, ArticleProps } from './Article';
import {articleDummy} from "./Article.test";

export default {
    title: 'component/Article',
    component: Article
}

const Template: Story<ArticleProps> = (args) => <Article {...args}/>;

export const Primary = Template.bind({});

Primary.args = {
    article: articleDummy
}

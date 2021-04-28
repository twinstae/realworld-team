import React from 'react';
import {Story } from '@storybook/react';
import { Article, ArticleProp } from '../components/Article';

export default {
  title: 'Example/Article',
  component: Article,
  argTypes: {
    color: { control: 'color' },
  },
};


const Template: Story<ArticleProp> = (args) => <Article {...args} />;

export const Primary = Template.bind({});
Primary.args = {
  article: {
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
    /*"author": {
      "username": "Aredruss",
      "bio": "",
      "image": null,
      "following": false
    },*/
    "favorited": false,
    "favoritesCount": 0
  }
};

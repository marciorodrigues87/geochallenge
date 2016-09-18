FROM node:slim

MAINTAINER Marcio Rodrigues <marciorodrigues87@gmail.com>

ADD public /opt/finup-frontend/public

ADD package.json /opt/finup-frontend/
ADD *.js /opt/finup-frontend/

WORKDIR /opt/finup-frontend

RUN npm install

EXPOSE 8081
CMD ["npm", "start"]

# Stage 1: Compile and Build angular codebase
FROM node:20-alpine as build

RUN npm install -g @angular/cli@17

WORKDIR /usr/local/app

COPY ./ /usr/local/app/

RUN npm install

RUN ng build --configuration production


# Stage 2: Serve app with nginx server
FROM nginx:latest

COPY ./nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /usr/local/app/dist/frontend /usr/share/nginx/html

EXPOSE 80

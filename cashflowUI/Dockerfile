# Stage 1: Compile and Build angular codebase
FROM node:24-alpine@sha256:d32cdf619f63fe0471182d08996dd516c6275bb5fd31ae06e55a570bd9e1ad43 as build

RUN npm install -g @angular/cli@22

WORKDIR /usr/local/app

COPY ./ /usr/local/app/

RUN npm install

RUN ng build --configuration production


# Stage 2: Serve app with nginx server
FROM nginx:latest

COPY ./nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /usr/local/app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80

# Stage 1: Build the Angular application
FROM node:21 AS build
WORKDIR /app
COPY ./package.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration=production

# Stage 2: Serve the application with Nginx
FROM nginx:latest

COPY --from=build /app/dist/foodie-app-ui/browser /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

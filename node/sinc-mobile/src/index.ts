import {SincMobileApplication} from './application';
import {ApplicationConfig} from '@loopback/core';

export {SincMobileApplication};

export async function main(options?: ApplicationConfig) {
  const app = new SincMobileApplication(options);
  await app.boot();
  await app.start();
  return app;
}

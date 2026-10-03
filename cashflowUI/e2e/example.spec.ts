import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:4200/home');
  await page.getByRole('button', { name: 'Login' }).click();
  await page.locator('app-login').getByRole('button', { name: 'Login' }).click();
  await page.getByRole('textbox', { name: 'Username or email' }).fill('demo');
  await page.getByRole('textbox', { name: 'Password' }).fill('demo');
  await page.getByRole('button', { name: 'Sign In' }).click();

  await page.getByRole('button', { name: 'Settings' }).click();
  await page.getByTestId('add-category-input-field').fill('Mobility');
  await page.getByRole('button', { name: 'Add Category' }).click();
  await expect.soft(page.locator('app-category-card')).toBeVisible();
  await expect.soft(page.getByTestId('category-card-label-Mobility')).toBeVisible();

  await expect.soft(page.getByTestId('category-type-selection-Mobility')).toHaveValue('IGNORE');
  await page.getByTestId('category-type-selection-Mobility').selectOption('VARIABLE');
  await page.reload();
  await expect.soft(page.getByTestId('category-type-selection-Mobility')).toHaveValue('VARIABLE');

  await page.getByTestId('category-add-identifier-input-Mobility').fill('ARAL');
  await page.getByTestId('category-add-identifier-button-Mobility').click();
  await page.reload();
  await expect.soft(page.getByTestId('identifier-chip-label-ARAL')).toBeVisible();

  await page.getByTestId('category-add-identifier-input-Mobility').fill('SHELL');
  await page.getByTestId('category-add-identifier-button-Mobility').click();
  await page.reload();
  await expect.soft(page.getByTestId('identifier-chip-label-SHELL')).toBeVisible();
  await expect.soft(page.getByTestId('identifier-chip-label-ARAL')).toBeVisible();

  await page.getByTestId('identifier-chip-button-SHELL').click();
  await expect.soft(page.getByTestId('identifier-chip-label-ARAL')).toBeVisible();
});

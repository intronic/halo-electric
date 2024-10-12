/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*",
    "./resources/public/halo_electric/index.html"
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require('@tailwindcss/forms'),
    require('@tailwindcss/typography'),
    require('daisyui'),
  ],
  daisyui: {
    themes: ["lemonade"],
  },
}


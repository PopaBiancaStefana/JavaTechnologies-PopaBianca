# Web Application for displaying Graph properties

# Specifications
  - MVC structure
  - business-logic component for determining the graph properties using Graph4J library
  - web filter for logging requests
  - web filter decorator that adds prelude and coda at the generated HTML
  - web listener that adds default properties, using context init parameters
  - cookie that stores the selected properties; automatically set the properties at application startup
  - CAPTCHA for the input form, with generated image of a random graph, using Graph4J and AWT Library


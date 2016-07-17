  /*
   * Validate alpha numeric
   */
  $.formUtils.addValidator({
      name : 'alphabetic',
      validatorFunction : function(val, $el, conf, language) {
          var patternStart = '^([a-zA-Z',
              patternEnd = ']+)$',
              additionalChars = $el.attr('data-validation-allowing'),
              pattern = '';

          if( additionalChars ) {
              pattern = patternStart + additionalChars + patternEnd;
              var extra = additionalChars.replace(/\\/g, '');
              if( extra.indexOf(' ') > -1 ) {
                  extra = extra.replace(' ', '');
              }
              this.errorMessage = language.badAlphabetic + language.badAlphabeticExtra + extra + language.andSpaces;
          } else {
              pattern = patternStart + patternEnd;
              this.errorMessage = language.badAlphaNumeric;
          }

          return new RegExp(pattern).test(val);
      },
      errorMessage : '',
      errorMessageKey: ''
  });  

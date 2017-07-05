startReadingUserInput();

function startReadingUserInput() {
  process.stdin.resume();
  process.stdin.setEncoding("ascii");
  let userInput = "";

  process.stdin.on("data", function (input) {
    userInput += input;
  });

  process.stdin.on("end", function () {
    const userInputObj = readUserInput(userInput);
    calculateAndPrintOutResults(userInputObj);
  });
}

function readUserInput(userInput) {
  const userInputLines = userInput.split("\n");
  const firstLine = userInputLines[0];
  const numberOfQueries = parseInt(firstLine);

  if (Number.isNaN(numberOfQueries)) {
    return NumberOfQueriesSpecifiedError(firstLine, numberOfQueries);
  }

  const userInputLessFirstLineAndTrailingNewLine =
        userInputLines.slice(
          1,
          userInputLines.length
        );
  const queries = getQueries(
    numberOfQueries,
    userInputLessFirstLineAndTrailingNewLine
  );
  return {
    numberOfQueries,
    queries
  };

  function NumberOfQueriesSpecifiedError(firstLine, firstLineAsANumber) {
    throw new Error(
      "First line must be the number of queries - a valid number.\n" +
        "You put in '" + firstLine + "' which is of type " +
        typeof firstLine+ "."
    );
  }
}

function getQueries(numberOfQueries, userInputLessFirstLineAndTrailingNewLine) {
  let queries = [];
  for (let queryIndex = 0; queryIndex < numberOfQueries; queryIndex += 1) {
    const query = readQueryFromInput(
      queryIndex,
      userInputLessFirstLineAndTrailingNewLine
    );
    queries.push(query);
  }
  return queries;

  function readQueryFromInput(
    queryIndex,
    userInputLessFirstLineAndTrailingNewLine
  ) {
    const startingLineIndex = queryIndex * 3;
    const informationLineIndex = startingLineIndex;
    const firstArrayIndex = startingLineIndex + 1;
    const secondArrayIndex = startingLineIndex + 2;
    const informationLine =
          userInputLessFirstLineAndTrailingNewLine[informationLineIndex];
    const firstArrayLine =
          userInputLessFirstLineAndTrailingNewLine[firstArrayIndex];
    const secondArrayLine =
          userInputLessFirstLineAndTrailingNewLine[secondArrayIndex];
    const information = informationLine.split(" ");
    const arrayLengths = parseInt(information[0]);
    const relationVariable = parseInt(information[1]);
    const firstArray = makeArrayFromInputLine(firstArrayLine);
    const secondArray = makeArrayFromInputLine(secondArrayLine);

    if (firstArray.length !== lengths ||
        secondArray.length !== lengths) {
      throw new Error("Found a case where the array lengths were not correct!");
    }
    return {
      arrayLengths,
      relationVariable,
      firstArray,
      secondArray
    };

    function makeArrayFromInputLine(line) {
      const arrayElements = line.split(" ");
      const arrayElementsAsNumbers = arrayElements.map(function(element) {
        return parseInt(element);
      })
      return arrayElementsAsNumbers;
    }
  }
}

function calculateAndPrintOutResults(userInputObj) {
  const numberOfQueries = userInputObj.numberOfQueries;
  const queries = userInputObj.queries;
  const queriesResults = queries.map(calculateQueryResult);
  queriesResults.forEach(function(result) {
    console.log(result);
  });

  function calculateQueryResult(query) {
    const lengths = query.arrayLengths;
    const relationVariable = query.relationVariable;
    const firstArray = query.firstArray;
    const secondArray = query.secondArray;

    const secondArrayDifferenceWithRelation = secondArray.map(function(num) {
      return relationVariable - num;
    });
    const secondArrayDifferenceWithRelationSorted =
          secondArrayDifferenceWithRelation.sort(sortNumbers);
    const firstArraySorted = firstArray.sort(sortNumbers);
    const differences = firstArraySorted.map(function(firstArrayNum, index) {
      const secondArrayNum = secondArrayDifferenceWithRelationSorted[index];
      return secondArrayNum - firstArrayNum;
    })
    const differencesWithValuesGreaterThanZero =
          differences.filter(function(num) {
            return num > 0;
          });
    const doesNotSatisfy = differencesWithValuesGreaterThanZero.length > 0;

    if (doesNotSatisfy) {
      return "NO";
    }
    return "YES";

    function sortNumbers(num1, num2) {
      return num1 - num2;
    }
  }
}

import React, {useEffect, useState} from 'react';
import {TextField, Button, Typography, Box, FormControl, InputLabel, Select, MenuItem} from '@mui/material';
import {addStockToPortfolio, getStocks, Portfolio, Stock} from '../services/api'; // Import your API service

const AddStockForm: React.FC<{ portfolio: Portfolio }> = ({ portfolio }) => {
    const [stockSymbol, setStockSymbol] = useState<string>('');
    const [shares, setShares] = useState<number>(0);
    const [stocksList, setStocksList] = useState<Stock[]>([]);
    const [error, setError] = useState<string>('');

    useEffect(() => {
        // Fetch all available stocks from the API
        const fetchStocks = async () => {
            try {
                const stocks = await getStocks(); // Assuming this is a function that fetches all stocks
                const stocksInPortfolio = portfolio.holdings.map(holding => holding.stock.symbol);

                // Filter out stocks that are already in the portfolio
                const filteredStocks = stocks.filter(stock => !stocksInPortfolio.includes(stock.symbol));
                setStocksList(filteredStocks);
            } catch (err) {
                setError('Error fetching stocks');
                console.error(err);
            }
        };

        fetchStocks(); // Call fetchStocks when the component mounts
    }, [portfolio]);


    const handleAddStock = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            // Add stock to portfolio by calling the backend API
            const updatedPortfolio = await addStockToPortfolio(portfolio.id, stockSymbol, shares);
            // Handle successful addition, e.g., update UI or notify the user
            console.log('Updated Portfolio:', updatedPortfolio);
        } catch (err) {
            setError('Error adding stock to portfolio');
            console.error(err);
        }
    };

    return (
        <Box component="form" onSubmit={handleAddStock} sx={{ width: '300px', margin: '0 auto', padding: 2 }}>
            <Typography variant="h6" gutterBottom align="center">Add Stock to Portfolio</Typography>

            <FormControl fullWidth sx={{ marginBottom: 2 }}>
                <InputLabel id="stock-symbol-label">Stock Symbol</InputLabel>
                <Select
                    labelId="stock-symbol-label"
                    value={stockSymbol}
                    onChange={(e) => setStockSymbol(e.target.value)}
                    required
                >
                    {stocksList.map((stock) => (
                        <MenuItem key={stock.symbol} value={stock.symbol}>
                            {stock.name} ({stock.symbol})
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>

            <TextField
                label="Shares"
                variant="outlined"
                fullWidth
                type="number"
                value={shares}
                onChange={(e) => setShares(Number(e.target.value))}
                required
                sx={{ marginBottom: 2 }}
            />

            <Button type="submit" variant="contained" color="primary" fullWidth>
                Add Stock
            </Button>

            {error && <Typography color="error" sx={{ marginTop: 2 }}>{error}</Typography>}
        </Box>
    );
};

export default AddStockForm;
